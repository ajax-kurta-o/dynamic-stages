pipeline {
    agent any
    stages {
            stage('Parallel Deployments1') {
            failFast true
            parallel {
                       stage("Deploy external-device-svc 1.36.1-306.RELEASE") {
            steps{
                script {
                    echo "Deploy external-device-svc 1.36.1-306.RELEASE"

                }
            }
        }
        stage("Deploy mobile-svc 1.36.1-306.RELEASE") {
            steps{
                script {
                    echo "Deploy mobile-svc 1.36.1-306.RELEASE"
                }
            }
        }
            }
        }
        stage("Run BDD tests with marks: marks1") {
            steps{
                script {
                    echo "Run BDD tests with marks: marks"
                }
            }
        }
    stage('Parallel Deployments2') {
            failFast true
            parallel {
                       stage("Deploy a911-device-svc 1.36.1*.RELEASE") {
            steps{
                script {
                    echo "Deploy a911-device-svc 1.36.1*.RELEASE"
                }
            }
        }
        stage("Deploy desktop-device-svc 1.36.1*.RELEASE") {
            steps{
                script {
                    echo "Deploy desktop-device-svc 1.36.1*.RELEASE"
                }
            }
        }
            }
        }
        stage("Run BDD tests with marks: marks2") {
            steps{
                script {
                    echo "Run BDD tests with marks: marks"
                }
            }
        }
    }
}