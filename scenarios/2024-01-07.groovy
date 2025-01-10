import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        // Parallel stages for "Deploy services"
        stage("Deploy services") {
            script {
                parallel {
                        stage("deploy_a") {
                            steps {
                                sh "echo 'This is stage a'"
                            }
                        },
                        stage("deploy_b") {
                            steps {
                                sh "echo 'This is stage b'"
                            }
                    }
                }
            }
        }

        // Sequential stage for "Run BDD tests"
        stage("Run BDD tests") {
            stage("Run tests") {
                steps {
                    sh "echo 'Run tests'"
                }
            }
        }
    }
}

return this



return this
