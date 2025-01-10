import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        parallel (
            'Deploy A': {
                steps {
                    sh "echo 'This is stage a'"
                }
            }
            'Deploy B': {
                steps {
                    sh "echo 'This is stage b'"
                }
            }
        )
    }
}

return this
