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
//                  for (int i = 1; i <= 2; i++) {
//                     sh (script: "echo Iteration")
//                  }
                script {
                    def dynamicStage = [
                        stages: [
                            [stage_name: "deploy_a", steps: [ { -> sh "echo 'This is stage a'" } ]],
                            [stage_name: "deploy_b", steps: [ { -> sh "echo 'This is stage b'" } ]]
                        ]
                    ]
//                     for (int i = 1; i <= 2; i++) {
//                         parallel dynamicStage.stages.each {
//                               separate_stage -> stage (separate_stage.stage_name) {
//                                     separate_stage.steps.each { step ->
//                                         step.call()
//                                     }
//                              }
//                         }
//
//                     }
                }
            }
        }
        stage ('Parallel') {
            parallel {
                stage('Branch A') {
                    agent any
                    steps {
                        echo "On Branch A"
                    }
                }
                stage('Branch B') {
                    agent any
                    steps {
                        echo "On Branch B"
                    }
                }
                stage('Branch C') {
                    agent any

                    stages {
                        stage('Nested 1') {
                            steps {
                                echo "In stage Nested 1 within Branch C"
                            }
                        }
                        stage('Nested 2') {
                            steps {
                                echo "In stage Nested 2 within Branch C"
                            }
                        }
                    }
                }
            }
        }

    }
}


