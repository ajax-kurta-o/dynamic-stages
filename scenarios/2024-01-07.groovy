import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        // Parallel stages for "Deploy services"
        stage("Deploy services") {
            script {
                parallel(
                    deploy_a: {
                        stage("deploy_a") {
                            steps {
                                sh "echo 'This is stage a'"
                            }
                        }
                    },
                    deploy_b: {
                        stage("deploy_b") {
                            steps {
                                sh "echo 'This is stage b'"
                            }
                        }
                    }
                )
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
