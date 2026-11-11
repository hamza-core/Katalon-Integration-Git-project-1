# CS-7B — Katalon Studio Automation Tests

**Author: Ameer Hamza**
GitHub: [@hamza-core](https://github.com/hamza-core) · Repository: [cs-7b](https://github.com/hamza-core/cs-7b)

---

## Overview

Web automation test suite for the **CURA Healthcare Service** demo application
(`http://demoaut.katalon.com/`), built with **Katalon Studio 11.5.0**.

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
# macOS / Linux
./katalon -project=. -suite="Test Suites/New Test Suite2.ts" \
          -runConfiguration=Chrome -report=Reports -logLevel=info

# Windows
katalon.exe -project=. -suite="Test Suites\New Test Suite2.ts" ^
            -runConfiguration=Chrome -report=Reports -logLevel=info
```

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
git clone https://github.com/hamza-core/cs-7b.git

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
