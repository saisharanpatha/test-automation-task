import java.util.List;
import org.testng.annotations.Test;
import utils.BaseTest;

public class BrokerTest extends BaseTest {

    private List<String> listOfBrokerNames;

    @Test(priority = 1, description = "Get the names of all brokers")
    public void verifyAllBrokersAreListed() {
        brokerPage
            .hideCookiesMessage()
            .loadMoreBrokers();
        listOfBrokerNames = brokerPage.getAllBrokers();
        softAssert.assertNotNull(listOfBrokerNames, "Verify broker names are gotten");
        softAssert.assertEquals(listOfBrokerNames.size(), 108, "Verify all broker names are gotten");
    }

    @Test(priority = 2, description = "Verify broker information", dependsOnMethods = "verifyAllBrokersAreListed")
    public void searchForBrokers() {
        listOfBrokerNames
            .forEach(broker -> {
                brokerPage.searchBrokerAndWaitForResults(broker);
                brokerPage.waitForSearchedNameToBeShown(broker);

                String brokerName = brokerPage.getName();
                softAssert.assertEquals(brokerName, broker,
                    "Verify that the searched broker name is the same as result");

                String brokerAddress = brokerPage.getAddress();
                softAssert.assertNotNull(brokerAddress, "Verify that the searched broker has an address");

                int phoneNumbers = brokerPage.getNumberOfPhones();
                softAssert.assertTrue(phoneNumbers > 0 && phoneNumbers < 3,
                    "Verify the two phone numbers are listed (there are brokers with 1 phone number)");

                int numberOfProperties = brokerPage.getNumberOfProperties();
                softAssert.assertTrue(numberOfProperties >= 0, "Verify number of properties are listed");

                String numberOfBrokersListed = brokerPage.getNumberOfBrokersListed();
                softAssert.assertEquals(numberOfBrokersListed, "1",
                    "Verify that the searched broker is the only one displayed");
            });
    }
}
