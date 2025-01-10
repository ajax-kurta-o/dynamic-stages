import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        parallel {
            stage('Deploy A') {
                agent any
                steps {
                    sh "echo 'This is stage a'"
                }
            }
            stage('Deploy B') {
                agent any
                steps {
                    sh "echo 'This is stage b'"
                }
            }
        }
    }
}

return this
