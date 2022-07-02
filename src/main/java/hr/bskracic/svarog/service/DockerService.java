package hr.bskracic.svarog.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import hr.bskracic.svarog.client.ContainerClient;

public class DockerService {

    public static String createContainer(String nodeId, String name, String image) {
        DockerClient client = ContainerClient.getInstance().getClient();
        CreateContainerResponse createContainerResponse = client.createContainerCmd(image)
                .withHostConfig(HostConfig.newHostConfig())
                .withImage(image)
                .withName(nodeId + "_" + name)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withTty(true)
                .exec();

        client.startContainerCmd(createContainerResponse.getId()).exec();

        return createContainerResponse.getId();
    }

//    public void startContainer(String containerId) {
//        try {
//            ContainerClient.getInstance().client.startContainerCmd("squil_container_" + userID).exec();
//        } catch (Exception e) {
//            throw new ContainerRunningException(userID);
//        }
//    }
//
//    public String executeQuery(String sqlQuery, String userID) {
//
//        DockerClient client = ContainerClient.getInstance().client;
//
//        if(!containerExists(userID)){
//            throw new ContainerNotStartedException(userID);
//        }
//
//        // Prepare statement
//        ExecCreateCmdResponse command = client
//                .execCreateCmd("squil_container_" + userID)
//                .withAttachStdout(true)
//                .withAttachStderr(true)
//                .withCmd("psql", "-U", "postgres", "-d", "postgres", "-c", sqlQuery)
//                .exec();
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        // Execute sql command
//        try {
//            client.execStartCmd(command.getId()).exec(new ResultCallback.Adapter<Frame>() {
//                @Override
//                public void onNext(Frame object) {
//                    try {
//                        outputStream.write(object.getPayload());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onComplete() {
//                    System.out.println("Message: " + outputStream);
//                }
//            }).awaitCompletion(1, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            throw new ContainerQueryFailedException(userID);
//        }
//
//        return outputStream.toString();
//    }
//
//    public void stopContainer(String userID) throws ContainerNotFoundException {
//        if(containerRunning(userID)) {
//            ContainerClient.getInstance().client.stopContainerCmd("squil_container_" + userID).exec();
//        } else {
//            throw new ContainerNotRunningException(userID);
//        }
//    }
//
//    public void removeContainer(String userID) throws ContainerRunningException, ContainerNotRunningException {
//        if (containerRunning(userID)) {
//            throw new ContainerRunningException(userID);
//        } else if(!containerExists(userID)) {
//            throw new ContainerNotFoundException(userID);
//        } else {
//            ContainerClient.getInstance().client.removeContainerCmd("squil_container_"+ userID).exec();
//        }
//    }
//
//    public boolean containerExists(String containerID) {
//        return ContainerClient.getInstance().client.listContainersCmd().withShowAll(true).exec().stream().anyMatch(c -> Arrays.equals(c.getNames(),
//                new String[]{"/squil_container_" + containerID}));
//    }
//
//    public boolean containerRunning(String containerID) {
//        return ContainerClient.getInstance().client.listContainersCmd().exec().stream().anyMatch(c -> Arrays.equals(c.getNames(),
//                new String[]{"/squil_container_" + containerID}));
//    }

}
