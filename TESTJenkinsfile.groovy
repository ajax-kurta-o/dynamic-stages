import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
pipeline {
    agent any
    stages {
        stage('ROLLOUT') {
                steps {
                    sh "echo Rollout!"
                    script{ rollout_stage_passed = true }
                }
        }
        stage("DYNAMIC_STAGES") {
            steps {
                script {
                    load '/var/jenkins_home/workspace/TestPipeline2_dynamic-stages/scenarios/2024-01-07.groovy'
                }
            }
        }
    }
}



def getDynamicStages() {
    try {
        dynamicStagesFile = '/var/jenkins_home/workspace/TestPipeline2_dynamic-stages/scenarios/performStages.groovy'
        sh 'echo in_dynamic'
        if (fileExists(dynamicStagesFile)) {
            sh 'echo file exist'
            return load(dynamicStagesFile)
        }
        return []
    }
    catch (Exception exc) {
        currentBuild.result = "UNSTABLE"
        unstable("Failed loading dynamic stages from remote repository. Error: ${exc}")

        return []
    }
}