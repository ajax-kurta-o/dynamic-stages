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
        parallel parallelStages1.collectEntries { dynamicStage ->
            [(dynamicStage.name): {
                dynamicStage.stages.each { stage ->
                    stage(stage.stage_name) {
                        stage.steps.each { step ->
                            step.call()
                        }
                    }
                }
            }]
        }

        // Sequentially run tests after all parallel stages
        run1.each { sequentialStage ->
            stage(sequentialStage.name) {
                sequentialStage.stages.each { stage ->
                    stage(stage.stage_name) {
                        stage.steps.each { step ->
                            step.call()
                        }
                    }
                }
            }
        }
    }
}

return this

return this
