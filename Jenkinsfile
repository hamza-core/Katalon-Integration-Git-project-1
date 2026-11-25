pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '10'))
    }

    parameters {
        string(name: 'KATALON_CMD', defaultValue: '/Applications/Katalon Studio.app/Contents/MacOS/katalon', description: 'Path to Katalon Runtime Engine (katalonc) or Katalon Studio executable')
        string(name: 'PROJECT_PATH', defaultValue: '.', description: 'Path to the Katalon project (relative to workspace or absolute)')
        string(name: 'TEST_SUITE_PATH', defaultValue: 'Test Suites/New Test Suite', description: 'Test Suite or Test Suite Collection path, e.g. "Test Suites/New Test Suite"')
        string(name: 'EXECUTION_PROFILE', defaultValue: 'default', description: 'Katalon execution profile')
        string(name: 'BROWSER_TYPE', defaultValue: 'Chrome', description: 'Browser type: Chrome, Chrome (headless), Firefox, etc.')
        string(name: 'API_KEY_CREDENTIAL_ID', defaultValue: 'katalon-api-key', description: 'Jenkins Secret Text credential ID containing the Katalon API key')
        string(name: 'EXTRA_ARGS', defaultValue: '', description: 'Optional extra arguments passed to Katalon')
    }

    environment {
        REPORT_DIR = 'Reports/Jenkins'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify Katalon') {
            steps {
                sh '''
                    set -eu
                    if [ ! -x "${KATALON_CMD}" ] && ! command -v "${KATALON_CMD}" >/dev/null 2>&1; then
                        echo "ERROR: Katalon command not found or not executable: ${KATALON_CMD}"
                        echo "Set the KATALON_CMD parameter to your katalonc/Katalon Studio path."
                        exit 1
                    fi
                    echo "Using Katalon command: ${KATALON_CMD}"
                '''
            }
        }

        stage('Run Katalon Tests') {
            steps {
                withCredentials([string(credentialsId: params.API_KEY_CREDENTIAL_ID, variable: 'KATALON_API_KEY')]) {
                    sh '''
                        set -eu
                        mkdir -p "${REPORT_DIR}"

                        # Resolve project path relative to workspace if needed
                        PROJECT_DIR="${PROJECT_PATH}"
                        case "${PROJECT_DIR}" in
                            /*) ;;
                            *) PROJECT_DIR="${WORKSPACE}/${PROJECT_DIR}" ;;
                        esac

                        echo "Project: ${PROJECT_DIR}"
                        echo "Suite: ${TEST_SUITE_PATH}"
                        echo "Profile: ${EXECUTION_PROFILE}"
                        echo "Browser: ${BROWSER_TYPE}"

                        # -noSplash -runMode=console works with Katalon Studio and KRE.
                        # For KRE (katalonc), the same args are supported.
                        "${KATALON_CMD}" \
                            -noSplash \
                            -runMode=console \
                            -projectPath="${PROJECT_DIR}" \
                            -retry=0 \
                            -testSuitePath="${TEST_SUITE_PATH}" \
                            -executionProfile="${EXECUTION_PROFILE}" \
                            -browserType="${BROWSER_TYPE}" \
                            -apiKey="${KATALON_API_KEY}" \
                            -reportFolder="${WORKSPACE}/${REPORT_DIR}" \
                            -reportFileName="Jenkins_Report" \
                            ${EXTRA_ARGS}
                    '''
                }
            }
        }
    }

    post {
        always {
            // Katalon writes reports under Reports/<timestamp>/...; archive both our target and default Reports.
            archiveArtifacts artifacts: 'Reports/**/*,*.log,*.log.*', allowEmptyArchive: true
            junit allowEmptyResults: true, testResults: 'Reports/**/JUnit_Report.xml'
        }
        failure {
            echo 'Katalon execution failed. Check archived Reports and console output.'
        }
        success {
            echo 'Katalon execution completed successfully.'
        }
    }
}
