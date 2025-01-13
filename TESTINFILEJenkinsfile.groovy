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
                    for (int i = 1; i <= 2; i++) {
                        parallel dynamicStage.stages.each {
                             separate_stage -> stage (separate_stage.stage_name) {
                                    separate_stage.steps.each { step ->
                                        step.call()
                                    }
                             }
                        }
                    }
                }
            }
        }
    }
}

def dynamicStage = [
        stages: [
            [stage_name: "deploy_a", steps: [ { -> sh "echo 'This is stage a'" } ]],
            [stage_name: "deploy_b", steps: [ { -> sh "echo 'This is stage b'" } ]]
        ]
    ]
