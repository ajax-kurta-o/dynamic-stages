import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    def parallelStages1 = [
        [
            name: "Deploy services",
            stages: [
                [stage_name: "deploy_a", steps: [ { -> sh "echo 'This is stage a'" } ]],
                [stage_name: "deploy_b", steps: [ { -> sh "echo 'This is stage b'" } ]]
            ]
        ]
    ]

    def run1 = [
        [
            name: "Run BDD tests",
            stages: [
                [stage_name: "Run tests", steps: [ { -> sh "echo 'Run tests'" } ]]
            ]
        ]
    ]

    return {
        parallelStages1.each { dynamicStage ->
            stage(dynamicStage.name) {
                script {
                    parallel dynamicStage.stages.collectEntries { stage ->
                        [(stage.stage_name): {
                            stage.steps.each { step ->
                                step.call()
                            }
                        }]
                    }
                }
            }
        }
    }
}

return this


