# Jenkins + Katalon + GitHub Setup

This guide connects this Katalon project to Jenkins and GitHub using the included [`Jenkinsfile`](../Jenkinsfile).

## What gets connected

- **GitHub** hosts this repository: `https://github.com/hamza-core/Katalon-Integration-Git-project-1`
- **Jenkins** pulls the repo, runs Katalon tests, and publishes reports.
- **Katalon** executes the configured Test Suite/Test Suite Collection via command line.

## 1. Prerequisites

- macOS machine (or any Jenkins agent) with:
  - Java/JDK compatible with your Katalon version
  - Google Chrome installed (or the browser you configure)
  - Katalon Studio / Katalon Runtime Engine installed
  - Git installed
- Katalon API key (Katalon account -> Profile -> API Key). The CLI requires `-apiKey`.
- Jenkins admin access.

> This machine has Katalon Studio at `/Applications/Katalon Studio.app`. For CI, Katalon Runtime Engine (KRE) is recommended; if you only have Studio, the Studio executable may still support console mode depending on version. Set `KATALON_CMD` accordingly.

## 2. Install Jenkins (macOS)

Option A - Homebrew (recommended):

```bash
brew install jenkins-lts
brew services start jenkins-lts
```

Option B - Docker:

```bash
docker run -d --name jenkins \
  -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts
```

Open Jenkins at `http://localhost:8080` and complete the initial setup wizard.

## 3. Install Jenkins plugins

In **Manage Jenkins -> Plugins -> Available plugins**, install:

- **Git** (usually preinstalled)
- **GitHub** or **GitHub Integration**
- **Pipeline**
- **JUnit** (for test result publishing)
- **Credentials** and **Credentials Binding** (usually preinstalled)

Optional:
- **Katalon** plugin - useful if you prefer plugin-managed KRE installation. The provided `Jenkinsfile` uses the CLI directly and does not require this plugin.

## 4. Add Katalon API key credential

1. Go to **Manage Jenkins -> Credentials -> (global) -> Add Credentials**.
2. Kind: **Secret text**.
3. Secret: your Katalon API key.
4. ID: `katalon-api-key` (or match the `API_KEY_CREDENTIAL_ID` Jenkinsfile parameter).
5. Save.

## 5. Create the Jenkins Pipeline job

1. **New Item -> Pipeline** (name it e.g. `katalon-github-ci`).
2. In **Pipeline** section:
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: `https://github.com/hamza-core/Katalon-Integration-Git-project-1.git`
   - Credentials: add GitHub credentials if the repo is private (for public repo, not required).
   - Branch: `*/main`
   - Script Path: `Jenkinsfile`
3. Save.

## 6. Configure build parameters

The `Jenkinsfile` exposes parameters. On first run, click **Build Now** once to let Jenkins load parameters, then use **Build with Parameters**:

| Parameter | Default | Notes |
|---|---|---|
| `KATALON_CMD` | `/Applications/Katalon Studio.app/Contents/MacOS/katalon` | Set to `katalonc` path for KRE, or your installed executable. |
| `PROJECT_PATH` | `.` | Workspace root contains the `.prj` file. |
| `TEST_SUITE_PATH` | `Test Suites/New Test Suite` | Use `Test Suites/New Test Suite2` or the collection as needed. |
| `EXECUTION_PROFILE` | `default` | Matches `Profiles/default.glbl`. |
| `BROWSER_TYPE` | `Chrome` | Use `Chrome (headless)` for headless CI agents. |
| `API_KEY_CREDENTIAL_ID` | `katalon-api-key` | Jenkins Secret Text credential ID. |
| `EXTRA_ARGS` | empty | Extra Katalon CLI args, e.g. `-retryStrategy=failedExecutions`. |

## 7. Connect GitHub -> Jenkins (automatic builds)

### Option A: GitHub webhook (recommended)

1. In Jenkins job, enable **GitHub hook trigger for GITScm polling** (or "Build when a change is pushed to GitHub", depending on plugin).
2. Ensure Jenkins is reachable from GitHub (public URL). For local Jenkins, use a tunnel like ngrok/cloudflared, or use polling instead.
3. In GitHub repo: **Settings -> Webhooks -> Add webhook**.
   - Payload URL: `http://<your-jenkins-host>/github-webhook/`
   - Content type: `application/json`
   - Events: **Just the push event** (or select as needed)
   - Active: checked
4. Save. GitHub will send a ping; Jenkins should receive it.

### Option B: Poll SCM (works for local Jenkins)

In the Jenkins job, enable **Poll SCM** and set a schedule, for example:

```
H/5 * * * *
```

This checks GitHub every ~5 minutes.

## 8. Connect Jenkins -> GitHub status (optional but useful)

To report build status back to GitHub commits/PRs:

- Add GitHub credentials in Jenkins (Personal Access Token with `repo:status` or appropriate permissions).
- Configure GitHub server in **Manage Jenkins -> System -> GitHub**.
- Use GitHub commit status publisher / Checks API plugin, or add `githubNotify` steps in the Jenkinsfile if desired.

## 9. Verify the integration

1. Run **Build with Parameters** in Jenkins.
2. Confirm stages: Checkout -> Verify Katalon -> Run Katalon Tests.
3. Check **Test Result** for JUnit results.
4. Check **Artifacts** for `Reports/**`.
5. Push a commit to `main` and confirm the webhook/poll triggers a build.

## 10. Troubleshooting

- **`Katalon command not found`**: fix `KATALON_CMD`. For KRE, point to `katalonc`; for Studio.app, `/Applications/Katalon Studio.app/Contents/MacOS/katalon`.
- **API key/license errors**: verify the `katalon-api-key` secret text credential and that the Katalon account/license is valid for the machine.
- **Browser/driver issues on CI**: use `Chrome (headless)`; ensure Chrome and matching driver are available. Katalon usually manages drivers, but CI images may need Chrome installed.
- **No JUnit results**: confirm Katalon produced `Reports/**/JUnit_Report.xml`; check console log for report path.
- **Webhook not triggering**: Jenkins must be reachable from GitHub. For localhost, use Poll SCM or a tunnel.
- **Project path issues**: `PROJECT_PATH` should point to the folder containing `WebAuto.prj`.

## Files added for CI

- `Jenkinsfile` - Pipeline definition (parameterized Katalon CLI execution, artifact archiving, JUnit publish).
- `docs/JENKINS_SETUP.md` - this setup guide.
