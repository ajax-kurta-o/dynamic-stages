import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        parallel {
            stage("deploy_a") {
                steps {
                    sh "echo 'This is stage a'"
                }
            }
            stage("deploy_b") {
                steps {
                    sh "echo 'This is stage b'"
                }
            }
        }
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
