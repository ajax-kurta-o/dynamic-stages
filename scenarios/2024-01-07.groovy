import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        parallel (
            'Deploy A': {
                    sh "echo 'This is stage a'"
            }
            'Deploy B': {
                    sh "echo 'This is stage b'"
            }
        )
    }
}

return this
