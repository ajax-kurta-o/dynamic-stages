import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    dynamicStage = [
        stages: [
            [stage_name: "deploy_a", steps: [ { -> sh "echo 'This is stage a'" } ]],
            [stage_name: "deploy_b", steps: [ { -> sh "echo 'This is stage b'" } ]]
        ]
    ]

    return {
        parallel dynamicStage.stages.each {
             stage -> {
                stage (stage.stage_name) {
                    stage.steps.each { step ->
                        step.call()
                    }
                }
             }
        }
    }
}

return this
