import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;

public class Population_test {

    @Test
    public void PopulationConstructor_test(){

        int populationSize = 10;

        Population population = new Population("Test", populationSize, 0);

        for (int x = 0; x < populationSize; x++) {
            Assert.assertTrue(population.population[x] != null);
        }

        Assert.assertTrue(population.target != null);

        for (int x = 0; x < population.mutationArray.length; x++){
            Assert.assertTrue(population.mutationArray != null);
        }


    }

    @Test
    public void decimalsLeftOfDecimalPoint_test(){
        Assert.assertEquals(3, Population.decimalsLeftOfDecimalPoint("0.005"),0);
        Assert.assertEquals(1, Population.decimalsLeftOfDecimalPoint("0.1"),0);
        Assert.assertEquals(1, Population.decimalsLeftOfDecimalPoint("0.0"),0);
        Assert.assertEquals(6, Population.decimalsLeftOfDecimalPoint("0.000005"),0);
    }

    @Test
    public void configureMultiplier_test(){
        String rate = "0.0";
        Assert.assertEquals(1, Population.configureMultiplier(rate, Population.decimalsLeftOfDecimalPoint(rate)), 0);
        rate = "0.1";
        Assert.assertEquals(10, Population.configureMultiplier(rate, Population.decimalsLeftOfDecimalPoint(rate)), 0);
        rate = "0.2";
        Assert.assertEquals(10, Population.configureMultiplier(rate, Population.decimalsLeftOfDecimalPoint(rate)), 0);
        rate = "0.01";
        Assert.assertEquals(100, Population.configureMultiplier(rate, Population.decimalsLeftOfDecimalPoint(rate)), 0);
    }

    @Test
    public void populateTheWheel_test(){
        int multiplier = 1;
        int[] wheel = new int[100*multiplier];
        Population.populateTheWheel(1,multiplier,wheel);

        int count = 0;
        for (int x = 0; x < wheel.length; x++){
            if (wheel[x] == 1) count++;
        }

        Assert.assertTrue(count == 1);

        multiplier = 1;
        wheel = new int[100*multiplier];
        Population.populateTheWheel(9,multiplier,wheel);

        count = 0;
        for (int x = 0; x < wheel.length; x++){
            if (wheel[x] == 1) count++;
        }

        Assert.assertTrue(count == 9);
    }

    @Test
    public void initMutationArray_test(){
        int[] mutationArray = Population.initMutationArray(5);

        int count = 0;
        for (int x = 0; x < mutationArray.length; x++){
            if (mutationArray[x] == 1) count++;
        }

        Assert.assertTrue(count == 5);
        Assert.assertTrue(mutationArray.length == 100);

        mutationArray = Population.initMutationArray(0.5);

        count = 0;
        for (int x = 0; x < mutationArray.length; x++){
            if (mutationArray[x] == 1) count++;
        }

        Assert.assertTrue(count == 5);
        Assert.assertTrue(mutationArray.length == 1000);




    }

    @Test
    public void findMaxFitness_test(){
        Population population = new Population("TEST", 1, 0);
        population.population[0] = new Individual("TEST");
        population.calculateFitness();
        Assert.assertTrue(population.findMaxFitness() == 1);

        population.population[0] = new Individual("TEXX");
        population.calculateFitness();
        Assert.assertTrue(population.findMaxFitness() == .5);

        population.population[0] = new Individual("TXX");
        population.calculateFitness();
        Assert.assertTrue(population.findMaxFitness() == .25);
    }

    @Test
    public void constructMatingPool_test(){

        Population population = new Population("Star Citizen", 5, 0);
        Individual one = new Individual("xxxxxxxxxxxx");
        population.population[0] = one;
        Individual two = new Individual("Sxxxxxxxxxxx");
        population.population[1] = two;
        Individual three = new Individual("Stxxxxxxxxxx");
        population.population[2] = three;
        Individual four = new Individual("Staxxxxxxxxx");
        population.population[3] = four;
        Individual five = new Individual("Starxxxxxxxx");
        population.population[4] = five;
        population.calculateFitness();
        ArrayList<Individual> mattingPool = population.constructMatingPool(1);

        //count

        int[] count = countIndividualsInMattingPool(population, mattingPool);

        Assert.assertTrue(count[0] == 1);
        Assert.assertTrue(count[1] == 25);
        Assert.assertTrue(count[2] == 50);
        Assert.assertTrue(count[3] == 75);
        Assert.assertTrue(count[4] == 100);

        mattingPool = population.constructMatingPool(2);

        count = countIndividualsInMattingPool(population, mattingPool);

        Assert.assertTrue(count[0] == 1);
        Assert.assertTrue(count[1] == Math.pow(25,2));
        Assert.assertTrue(count[2] == Math.pow(50,2));
        Assert.assertTrue(count[3] == Math.pow(75,2));
        Assert.assertTrue(count[4] == Math.pow(100,2));
    }

    private int[] countIndividualsInMattingPool(Population population, ArrayList<Individual> mattingPool) {
        int[] count = new int[5];

        for (int x = 0; x < mattingPool.size(); x++){
            if (mattingPool.get(x) == population.population[0]){
                count[0]++;
            }else if (mattingPool.get(x) == population.population[1]){
                count[1]++;
            }else if (mattingPool.get(x) == population.population[2]){
                count[2]++;
            }else if (mattingPool.get(x) == population.population[3]){
                count[3]++;
            }else if (mattingPool.get(x) == population.population[4]){
                count[4]++;
            }
            Assert.assertTrue(mattingPool.get(x).getGenes().length() == 12);
        }
        return count;
    }


    @Test
    public void naturalSelection_test(){

        Population populationOne = new Population("Test", 10, .5);
        populationOne.calculateFitness();
        Population populationTwo = new Population("Test",10,.5);
        populationTwo.population = populationOne.population;
        try {
            populationOne.naturalSelection();
        }catch (Exception e){

        }


//        Assert.assertTrue(!(populationOne.equals(populationTwo)));
    }
}
