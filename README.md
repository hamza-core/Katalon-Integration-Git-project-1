# Katalon Integration-Git project 1

**Author: Ameer Hamza**
GitHub: [@hamza-core](https://github.com/hamza-core) · Repository: [Katalon-Integration-Git-project-1](https://github.com/hamza-core/Katalon-Integration-Git-project-1)

---

## Overview

Web automation test suite for the **CURA Healthcare Service** demo application
(`http://demoaut.katalon.com/`), built with **Katalon Studio 11.5.0** — *Katalon Integration with GitHub*.

The suite covers the full "Book an Appointment" journey: login → hospital selection →
readmission/insurance options → visit date → comment → booking confirmation.

## Author

| Name        | GitHub                                          |
| ----------- | ----------------------------------------------- |
| Ameer Hamza | [@hamza-core](https://github.com/hamza-core)     |

## Project Structure

```
WebAuto/
├── Test Cases/              # Katalon test cases (.tc) + their scripts
│   ├── WebAuto.tc                    # Recorded login + appointment flow
│   ├── webManual.tc                  # Manual (scripted) version of the flow
│   ├── scripttest.tc                 # Scripted variant with hospital selection
│   └── LabTask_23_Sep_26_Mam_Tayyaba.tc  # Lab task: hospital dropdown selection
├── Test Suites/            # Test suites (.ts) and their Groovy runners
│   ├── New Test Suite.ts / .groovy
│   ├── New Test Suite2.ts / .groovy
│   └── New Test Suite Collection.ts  # Parallel collection of both suites
├── Object Repository/       # Recorded web element locators (.rs)
│   ├── Page_CURA Healthcare Service/
│   └── webmanualobjects/Page_CURA Healthcare Service/
├── Libs/                   # Custom keywords & global variables (Groovy)
├── Include/config/         # Supporting configuration files
├── Profiles/default.glbl   # Global variable profile
└── Scripts/                # Generated script files per test case
```

## Test Coverage

| Test Case                            | Description                                                        |
| ------------------------------------ | ------------------------------------------------------------------ |
| `WebAuto`                            | Login and complete an appointment with readmission + Medicaid       |
| `webManual`                          | Manually scripted appointment flow (no hospital dropdown selection)  |
| `scripttest`                         | Appointment flow selecting the *Seoul* hospital centre               |
| `LabTask_23_Sep_26_Mam_Tayyaba`      | Lab task — selects hospital centre, applies readmission, books slot  |

## Prerequisites

- [Katalon Studio](https://www.katalon.com/downloads) 8.0+ (developed on 11.5.0)
- Google Chrome / Chromium
- Active internet access to `http://demoaut.katalon.com/`

## How to Run the Tests

### From Katalon Studio (GUI)

1. Open **Katalon Studio** and import/open this project.
2. Go to the **Test Suites** folder.
3. Right-click a test suite → **Run**.
4. Select the `Chrome` browser configuration and the `default` profile.
5. View results in the **Reports** folder.

### From the Command Line

```bash
# macOS / Linux (Katalon Runtime Engine)
katalonc -noSplash -runMode=console -projectPath="." \
  -retry=0 -testSuitePath="Test Suites/New Test Suite" \
  -executionProfile="default" -browserType="Chrome" \
  -apiKey="<YOUR_KATALON_API_KEY>"
```

> Adjust `katalonc` to your Katalon Runtime Engine or Katalon Studio executable path.

## CI: Jenkins + GitHub

This repo includes a parameterized [`Jenkinsfile`](Jenkinsfile) for running Katalon tests in Jenkins.

- Pipeline: checks out from GitHub, runs Katalon CLI, archives `Reports/**`, publishes JUnit results.
- Setup guide: [docs/JENKINS_SETUP.md](docs/JENKINS_SETUP.md)

Key Jenkins setup steps:
1. Install Jenkins and required plugins (Git, Pipeline, GitHub, JUnit).
2. Add a Jenkins **Secret text** credential named `katalon-api-key`.
3. Create a Pipeline job using **Pipeline script from SCM** pointing to this repo and `Jenkinsfile`.
4. Configure a GitHub webhook to `http://<jenkins-host>/github-webhook/` (or use Poll SCM for local Jenkins).

See [docs/JENKINS_SETUP.md](docs/JENKINS_SETUP.md) for full details.

### Run a single test case

```bash
./katalon -project=. -testCase="Test Cases/webManual.tc" \
          -runConfiguration=Chrome -report=Reports
```

## Demo Credentials

The tests target the publicly available CURA Healthcare demo site:

| Field    | Value               |
| -------- | ------------------- |
| Username | `John Doe`          |
| Password | `ThisIsNotAPassword` |

These are demo-only credentials published by Katalon for `demoaut.katalon.com` and are
not linked to any real account.

## GitHub Integration

This project is linked to GitHub and pushed via a **Personal Access Token**.

```bash
# Clone
git clone https://github.com/hamza-core/Katalon-Integration-Git-project-1.git

# Configure identity
git config user.name  "Ameer Hamza"
git config user.email "ameerhamza3126@gmail.com"

# Push
git add .
git commit -m "commit message"
git push origin main
```

Authentication is handled by the GitHub CLI credential helper, which stores the
Personal Access Token in the macOS Keychain:

```bash
gh auth login
gh auth status
```

## Notes

- Generated artefacts (`bin/`, `Reports/`, `build/`, `.gradle/`) are excluded via `.gitignore`.
- `Libs/` Groovy keyword sources are version controlled; only binary jars and temporary
  generated test-case files are ignored.
- The `object repository` locators were recorded against the live demo site and may need
  re-recording if the site markup changes.
