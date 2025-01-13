pipeline {
    agent any
    stages {
        stage('ROLLOUT') {
            steps {
                sh "echo Rollout!"
                script { rollout_stage_passed = true }
            }
        }
        stage("DYNAMIC_STAGES") {
            steps {
                script {
                    if (rollout_stage_passed) {
                        def performStages = dynamicLib.performStages()
                        performStages.call() // Invoke the closure
                    } else {
                        echo "Skip running dynamic stages due to failed rollout process"
                    }
                }
            }
        }
    }
}

def getDynamicStages() {
    try {
        def dynamicStagesFile = '/Users/oleksandrkurta/WORK/own-dynamic-stages/scenarios/first_example.groovy'
        if (fileExists(dynamicStagesFile)) {
            return load(dynamicStagesFile)
        }
        return null
    } catch (Exception exc) {
        currentBuild.result = "UNSTABLE"
        unstable("Failed loading dynamic stages from remote repository. Error: ${exc}")
        return null
    }
}
