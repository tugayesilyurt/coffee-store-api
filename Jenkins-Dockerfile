# Set the base image for the new image
FROM jenkins/jenkins:lts
# sets the current user to root. 
# This is necessary bc some of the following commands need root privileges.
USER root
# Update the package list on the image
RUN apt-get update && \
    # Install the packages for adding the Docker repo and installing Docker
    apt-get install -y apt-transport-https \
                       ca-certificates \
                       curl \
                       gnupg2 \
                       software-properties-common && \
    # Download the Docker repository key and add it to the system
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    # Add the Docker repository to the system
    add-apt-repository \
      "deb [arch=arm64] https://download.docker.com/linux/debian \
      $(lsb_release -cs) \
      stable" && \
    # Update the package list again to include the new repository
    apt-get update && \
    # Install the Docker CE package
    apt-get install -y docker-ce && \
    # Add the Jenkins user to the Docker group so the Jenkins user can run Docker commands
    usermod -aG docker jenkins