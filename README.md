# Task-5-CI-CD-Pipeline-2023
Create a CI-CD pipeline for a sample application (built in task 1 and/or 4 above) using any CI-CD tool of your choice like Jenkins, Azure DevOps, Gitlab, Github Actions, AWS CodePipeline or any other tool of your choice. Include a code build and a docker build step in
your pipeline.

# Table of Contents:
1. Project Setup:
   1.1 Pacakges Required
   1.2 Software Used for CI-CD
2. Workflow Creation
3. Cache Creation

## 1. Project Setup:
### 1.1 Pacakges Required
          -- JDK 17
### 1.2 Software Used for CI-CD
           Github Action
## 2. Workflow Creation

/.github/workflow/main.yml

 name: Spring boot kaiburr
on:
  push:
    branches:
      - master
jobs:
  build-java-app:
    name: Build and deploy kaiburr app
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      - name: Setup JDK 17
        uses: actions/setup-java@v3
        with:
          distribution: 'corretto'
          java-version: 17

      - name: Cache local Maven Repository
        uses: actions/cache@v3
        with:
          path: ~/.m2/repository
          key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{runner.os}}-maven-

      - name: Build App
        run: |
          mvn clean
          mvn -B package --file pom.xml

      - name: Login to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_HUB_USER_NAME }}
          password: ${{ secrets.DOCKER_HUB_ACCESS }}
      - name: Build and Push
        uses: docker/build-push-action@v4
        with:
          context: .
          dockerfile: Dockerfile
          push: true
          tags: ${{secrets.DOCKER_HUB_USER_NAME }}/myapp:0.1.0

![1_success_commit](https://github.com/krishnasaw8340/Task-5-CI-CD-Pipeline-2023/assets/63328010/74520550-f602-45a3-b4e2-db9e692642cd)



### Docker Hub
![2_docker_my_app_Created](https://github.com/krishnasaw8340/Task-5-CI-CD-Pipeline-2023/assets/63328010/2bdf98bc-60cd-4c27-aac7-a1e8bfcdc141)



## 3. Cache Creation 

![3_cache_created](https://github.com/krishnasaw8340/Task-5-CI-CD-Pipeline-2023/assets/63328010/f7e8dba4-29ea-4246-a930-c6c990211612)


![3_success_updated_cache](https://github.com/krishnasaw8340/Task-5-CI-CD-Pipeline-2023/assets/63328010/47e9dc45-4ea0-4394-bd9b-d8218e65c140)






