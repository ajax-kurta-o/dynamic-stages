import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    dynamicStage = [
        stages: [
            [stage_name: "deploy_a", steps: [ { -> sh "echo 'This is stage a'" } ]],
            [stage_name: "deploy_b", steps: [ { -> sh "echo 'This is stage b'" } ]]
        ]
    ]

    return {
        parallel dynamicStage.stages.each { stage_info ->
            stage (stage_info.stage_name) {
                stage_info.steps.each { step ->
                    step.call()
                }
            }
        }
    }
}

return this
