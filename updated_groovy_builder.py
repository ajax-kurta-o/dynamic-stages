from helpers import render_template
from templates import DEPLOY_SVC_TEMPLATE, RUN_BDD_TESTS_TEMPLATE, PARALLEL_DEPLOY_TEMPLATE, BASE_PIPELINE_TEMPLATE

SCENARIOS_DIR = "scenarios"

class RCGroovyScenarioBuilder:
    def __init__(self, scenario, logger, parallel_deployment=False):
        self.scenario_name = scenario["scenarioName"]
        self.scenario_date = scenario["date"]
        self.config_stages = scenario["stages"]
        self.parallel_deployment = parallel_deployment
        if parallel_deployment:
            self.__append_service_params()
        self.logger = logger

        self.build_stages = []

        self.current_parallel_group = []

    def get_builder_method(self, stage_name):
        return {
            "deployService": self.build_parallel_deploy_stages if self.parallel_deployment else self.build_deploy_service_stage,
            "runTests": self.build_run_bdd_tests_stage,
        }.get(stage_name)

    def build(self):
        for stage in self.config_stages:
            builder_method = self.get_builder_method(stage_name=stage["name"])
            builder_method(stage["parameters"])

    def build_parallel_deploy_stages(self, params):

        service_name = params["serviceName"]
        service_version = params["serviceVersion"]
        deployment_destination = params.get("deploymentDestination", "null")

        stage_passed_variable = f"deploy_{service_name.lower().replace('-', '_')}_passed"

        step = render_template(
            template=DEPLOY_SVC_TEMPLATE,
            stage_name=f"Deploy {service_name} {service_version}",
            service_name=service_name,
            service_version=service_version,
            deployment_destination=deployment_destination,
            stage_passed_variable=stage_passed_variable,
        )
        self.current_parallel_group.append(step)
        if params.get("isBeforeRunTests"):
            self.build_stages.append(render_template(PARALLEL_DEPLOY_TEMPLATE, parallel_stages="\n".join(self.current_parallel_group)))
            self.current_parallel_group = []
        self.logger.info("Added parallel deployment stage")

    def build_deploy_service_stage(self, params):
        serviceName = params["serviceName"]
        serviceVersion = params["serviceVersion"]
        deploymentDestination = params.get("deploymentDestination", "null")
        stage_passed_variable = f"deploy_{serviceName.lower().replace('-', '_')}_passed"

        stage = render_template(
            template=DEPLOY_SVC_TEMPLATE,
            stage_name=f"Deploy {serviceName} {serviceVersion}",
            service_name=serviceName,
            service_version=serviceVersion,
            deployment_destination=deploymentDestination,
            stage_passed_variable=stage_passed_variable,
        )

        self.build_stages.append(stage)
        self.logger.info(f"Deploy {serviceName} {serviceVersion}")

    def build_run_bdd_tests_stage(self, marks):
        stage_name = "Run BDD tests"
        if marks:
            marks = ", ".join(marks)
            stage_name = f"{stage_name} with marks: {marks}"
        else:
            marks = "Empty"

        run_tests_stage = render_template(
            template=RUN_BDD_TESTS_TEMPLATE,
            stage_name=stage_name,
            marks=marks,
        )

        self.build_stages.append(run_tests_stage)
        self.logger.info(f"Run BDD tests with marks: {marks}")

    def save_groovy_file(self):
        stages_groovy = "\n".join(self.build_stages)
        pipeline_script = render_template(BASE_PIPELINE_TEMPLATE, stages_groovy=stages_groovy)

        groovy_file_path = f"{SCENARIOS_DIR}/{self.scenario_date}.groovy"

        with open(groovy_file_path, "w") as f:
            f.write(pipeline_script)

        self.logger.success(f"File '{groovy_file_path}' successfully generated")

    def __append_service_params(self):
        for index, stage in enumerate(self.config_stages):
            if stage["name"] == "runTests" and index != 0:
                pre_stage = self.config_stages[index-1]
                pre_stage["parameters"].update({"isBeforeRunTests": True})


