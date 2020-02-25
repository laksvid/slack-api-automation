import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import com.task.slackapi.Application;
import com.task.slackapi.config.PropertiesInitializer;
import com.task.slackapi.service.SlackService;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = Application.class, initializers = PropertiesInitializer.class)
public class SlackApiTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private SlackService slackService;

  private String channelId;


  @Test(priority = 1, description = "Create a new channel by name mychannel")
  public void createChannel() {
    Response createChannel = slackService.createChannel("mychannel");
    boolean ok = createChannel.jsonPath().get("ok");
    Assert.assertTrue(ok);
    channelId = createChannel.jsonPath().get("channel.id");
  }

  @Test(priority = 2,
        description = "Join the channel by name mychannel",
        dependsOnMethods = "createChannel")
  public void joinChannel() {
    Response joinChannel = slackService.joinChannel(channelId);
    joinChannel
        .then()
        .body("ok", equalTo(true));
  }

  @Test(priority = 3,
        description = "Rename the channel by name mychannel to newchannel",
        dependsOnMethods = "createChannel")
  public void renameChannel() {
    Response renameChannel = slackService.renameChannel(channelId, "newchannel");
    renameChannel
        .then()
        .body("ok", equalTo(true))
        .body("channel.name", equalTo("newchannel"))
        .body("channel.name", not(equalTo("mychannel")));

  }

  @Test(priority = 4,
        description = "List the channels and check if mychannel is renamed to newchannel",
        dependsOnMethods = "renameChannel")
  public void listChannel() {
    Response listChannel = slackService.listChannel();
    boolean status = listChannel.jsonPath().get("ok");
    Assert.assertTrue(status);
    List<String> channelList = listChannel.jsonPath().get("channels.name");
    Assert.assertTrue(channelList.contains("newchannel"));
    Assert.assertFalse(channelList.contains("mychannel"));
  }

  @Test(priority = 5,
        description = "Archive the channel by name newchannel and check if archived",
        dependsOnMethods = "createChannel")
  public void archiveChannel() {
    Response archiveChannel = slackService.archiveChannel(channelId);
    archiveChannel
        .then()
        .body("ok", equalTo(true));
    Response channelInfo = slackService.getChannelInfo(channelId);
    channelInfo
        .then()
        .body("channel.name", equalTo("newchannel"))
        .body("channel.is_archived", equalTo(true));
  }

  @Test(priority = 6,
        description = "Channel not be created and archive if already exists and archived",
        dependsOnMethods = "createChannel")
  public void createExistedChannel() {
    Response createChannel = slackService.createChannel("newchannel");
    boolean create = createChannel.jsonPath().get("ok");
    Assert.assertFalse(create);
    Response archiveChannel = slackService.archiveChannel(channelId);
    boolean archive = archiveChannel.jsonPath().get("ok");
    Assert.assertFalse(archive);
  }
}