import org.jenkinsci.plugins.pipeline.modeldefinition.Utils




def performStages() {
    parallel_stages_1 = [
        [
            name: "Deploy services",
            stages: [
                    [stage_name: "deploy_a", steps: [ {"echo 'This is stage a'"} ],
                    [stage_name: "deploy_b", steps: [ {"echo 'This is stage b'"} ],
                ]
            ]
    ]

    run_1 = [
        [
            name: "Run BDD tests"
            stages: [stage_name: "Run tests", steps: [ {echo 'Run tests'} ],
         ]
    ]
    return {
        parallel_stages_1.each { dynamicStage ->
            stage(dynamicStage.name) {
                script {
                    parallel dynamicStage.stages.collectEntries { stage ->
                        stage (stage.stage_name) {
                            script {
                                stage.steps.each { step ->
                                    step.call()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

return this
