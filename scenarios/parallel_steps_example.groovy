
parallelStages1 = [
    [
        name: "Deploy services",
        branches: [
            [branch_name: "deploy_a", steps: [ script { echo "This is stage a"} ]],
            [branch_name: "deploy_b", steps: [ script { echo "This is stage b"} ]]
        ]
    ]
]

run1 = [
        [
            name: "Run BDD tests",
            branches: [
                [branch_name: "Run tests", steps: [ { -> sh "echo 'Run tests'" } ]]
            ]
        ]
    ]



def performStages() {


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
        run1.each { dynamicStage ->
                stage(dynamicStage.name) {
                    script {
                        dynamicStage.stages[0].steps.each { step ->
                            step.call()
                        }
                    }
                }
        }
    }
}

return this
