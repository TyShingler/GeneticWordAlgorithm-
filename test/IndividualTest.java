import org.junit.Test;
import org.junit.Assert;

public class IndividualTest {

    @Test
    public void individualIntegerConstructor_test() {
        Individual individualOne = new Individual(10);
        Assert.assertEquals("Gene length is generated based on input.", 10, individualOne.getGenes().length());

        Individual individualTwo = new Individual(20);
        Assert.assertEquals("Gene length is generated based on input.", 20, individualTwo.getGenes().length());

        Individual individualThree = new Individual(0);
        Assert.assertEquals("Gene length is generated based on input.", 0, individualThree.getGenes().length());

        Individual individualFour = new Individual(-1);
        Assert.assertEquals("Gene length is generated based on input.", 0, individualFour.getGenes().length());
    }

    @Test
    public void individualStringConstructor_test(){
        Individual individualOne = new Individual("StevenBower");
        Assert.assertEquals("Gene string is what you gave it.", "StevenBower", individualOne.getGenes());

        Individual individualTwo = new Individual("");
        Assert.assertEquals("Gene string is what you gave it.", "", individualTwo.getGenes());
    }

    @Test
    public void calculateFitness_test(){
        Individual individualOne = new Individual("String");

        Assert.assertEquals("Individual gene fitness is calculated correctly.1",
                1.0, individualOne.calculateFitness("String"),0);
        Assert.assertEquals("Individual gene fitness is calculated correctly.2",
                .83, individualOne.calculateFitness("Strinx"),.01);
        Assert.assertEquals("Individual gene fitness is calculated correctly.3",
                .83, individualOne.calculateFitness("Strin"),.01);
        Assert.assertEquals("Individual gene fitness is calculated correctly.4",
                .85, individualOne.calculateFitness("StringX"),.01);

        individualOne = new Individual("smGN `vtqp2o");
        individualOne.calculateFitness("star citizen");
        System.out.println(individualOne.getFitness());
    }

    @Test
    public void getFitness_test(){
        Individual individualOne = new Individual("String");

        Assert.assertEquals("An individuals fitness is zero before fitness is calculated.0",
                0, individualOne.getFitness(),0);

        individualOne.calculateFitness("String");
        Assert.assertEquals("Individual returns fitness.1",
                1.0, individualOne.getFitness(),0);
    }

    @Test
    public void randomChar_test(){
        int character;
        for(int x = 0; x < 100; x++){
            character = (int)Individual.randomChar();
            Assert.assertTrue(character <= 126 && character >= 32);
            Assert.assertTrue(character != 34);
            Assert.assertTrue(character != 127);
            Assert.assertTrue(character != 31);
        }
    }

    @Test
    public void getGenes_test() {
        Individual individualOne = new Individual("String");
        Assert.assertTrue(individualOne.getGenes() != "");

        Individual individualTwo = new Individual(10);
        Assert.assertTrue(individualTwo.getGenes() != "");
    }

    @Test
    public void singleCrossoverMate_test(){
        Individual individualOne = new Individual("One");
        Individual individualTwo = new Individual("Two");

        int[] mutationArray = Population.initMutationArray(1);

        String gene;
        for (int x = 0; x < 100; x++) {
            gene = Individual.singleCrossoverMate(individualOne, individualTwo);
            Assert.assertTrue(gene != "");
            Assert.assertTrue(gene.length() == 3);
        }
    }

    @Test
    public void mutate_test(){

        testMutate(50,"A", new int[]{1});
        testMutate(50,"A", new int[]{1,1,1,1});
        testMutate(50,"TESTSTRING", new int[]{1});
        testMutate(50,"TESTSTRING", new int[]{1,1,1,1});



    }

    private void testMutate(int timesToTest, String geneToTest, int[] mutationArray) {
        String[] results = new String[timesToTest];
        for (int x = 0; x < timesToTest; x++) {
            results[x] = Individual.mutate(geneToTest, mutationArray);
        }
        int differences = 0;
        for (int x = 0; x < results.length; x++) {
//            System.out.println(results[x]);
            Assert.assertTrue(results[x] != "");
            Assert.assertTrue(results[x].length() == geneToTest.length());
            if (results[x] != geneToTest) {
                differences++;
            }
        }
        Assert.assertTrue(differences != 0);
    }

    @Test
    public void mate_test(){
        Individual individualOne = new Individual("ONE");
        Individual individualTwo = new Individual("TWO");

        Individual newIndividual = null;
        try {
            newIndividual = Individual.mate(individualOne, individualTwo, new int[]{0,1});
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(newIndividual != null);
        Assert.assertTrue(newIndividual.getGenes().length() == individualOne.getGenes().length());
    }
}
