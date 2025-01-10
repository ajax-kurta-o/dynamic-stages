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
//         stage("DYNAMIC_STAGES") {
//             steps {
//                 script {
//                     if (rollout_stage_passed) {
//                         def dynamicLib = getDynamicStages()
//                         dynamicLib.performStages().call()
//                     } else {
//                         echo "Skip running dynamic stages due to failed rollout process"
//                     }
//                 }
//             }
//         }

        stage('Non-Parallel Stage') {
            steps {
                echo 'This stage will be executed first.'
            }
        }
        stage('Parallel Stage') {

            failFast true
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



def getDynamicStages() {
    try {
        dynamicStagesFile = '/var/jenkins_home/workspace/TestPipeline2_dynamic-stages/scenarios/2024-01-07.groovy'
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