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
                    if (rollout_stage_passed == true) {
                        getDynamicStages().each { dynamicStage ->
                            stage(dynamicStage.name) {
                                dynamicStage.steps.each { step ->
                                    step.call()
                                }
                            }
                        }
                    }
                    else {
                        echo "Skip running dynamic stages due to failed rollout process"
                    }
                }
            }
        }
    }
}



def getDynamicStages() {
    try {
        dynamicStagesFile = '/var/jenkins_home/workspace/TestPipeline2_dynamic-stages/scenarios/2024-01-07.groovy'
        sh '$(pwd)'
        sh 'echo in_dynamic'
        if (fileExists(dynamicStagesFile)) {
            sh 'echo file exist'
            return load(dynamicStagesFile).getStages()
        }
        return []
    }
    catch (Exception exc) {
        currentBuild.result = "UNSTABLE"
        unstable("Failed loading dynamic stages from remote repository. Error: ${exc}")

        return []
    }
}