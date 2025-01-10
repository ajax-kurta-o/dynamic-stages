import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        stage('Deploy Services') {
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
        stage('Run BDD Tests') {
            stage('Run Tests') {
                steps {
                    sh "echo 'Run tests'"
                }
            }
        }
    }
}

return this
