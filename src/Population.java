import java.util.ArrayList;
import java.lang.Math;

public class Population {

    protected Individual[] population;
    protected String target;
    protected int[] mutationArray;

    public Population(String target, int populationSize, double mutationRate){
        this.target = target;
        population = new Individual[populationSize];
        for(int x = 0; x < populationSize; x++){
            population[x] = new Individual(target.length());
        }
        mutationArray = initMutationArray(mutationRate);
    }

    public void calculateFitness(){
        for (int x = 0; x < population.length; x++){
            population[x].calculateFitness(target);
        }
    }

    public void naturalSelection() {
        ArrayList<Individual> matingPool = constructMatingPool(2  );

        Individual parentOne = selectParent(matingPool);
        Individual parentTwo = selectParent(matingPool);
        while (parentOne.equals(parentTwo)){
            parentTwo = selectParent(matingPool);
        }

        createNewPopulation(parentOne,parentTwo);
    }

    protected void createNewPopulation(Individual parentOne, Individual parentTwo){
        for (int x = 0; x < population.length; x++) {
            population[x] = Individual.mate(parentOne, parentTwo, mutationArray);
        }
    }

    protected Individual selectParent(ArrayList<Individual> matingPool) {
        int index = (int)(Math.random()*matingPool.size());
        return matingPool.get(index);
    }

    protected ArrayList<Individual> constructMatingPool(int power) {
        double maxFitness = findMaxFitness();
        ArrayList<Individual> matingPool = new ArrayList<>();
        int slots;
        for (int x = 0; x < population.length; x++){
            slots = ((int) (map(population[x].getFitness(),maxFitness,1,0) * 100));
            slots = (int) Math.pow(slots,power);
            if (slots == 0) slots = 1;
            for (int y = 0; y < slots; y++){
                matingPool.add(population[x]);
            }
        }
        return matingPool;
    }

    protected double findMaxFitness(){
        double max = 0;
        double fitness = 0;
        for (int x = 0; x < population.length; x++){
            fitness = population[x].getFitness();
            if (fitness > max){
                max = fitness;
            }
        }
        return max;
    }

    protected double map(double score, double maxScore, double maxRange, double minRange){
        double percentOfMaxScore = score/maxScore;
        double subRange = maxRange - minRange;
        return percentOfMaxScore*subRange + minRange;
    }

    protected Individual isFinished(){
        for (int x = 0; x < population.length; x++){
            if (population[x].getFitness() == 1) return population[x];
        }
        return null;
    }

    protected static int[] initMutationArray(double mutationRate) {
        String rate = String.valueOf(mutationRate);
        int numberOfDecimals = decimalsLeftOfDecimalPoint(rate);
        int multiplier = configureMultiplier(rate, numberOfDecimals);
        int[] wheel = new int[100*multiplier];
        populateTheWheel(mutationRate, multiplier, wheel);
        return wheel;
    }

    protected static void populateTheWheel(double mutationRate, int multiplier, int[] wheel) {
        int index;
        for (index = 0; index < mutationRate*multiplier; index++){
            wheel[index] = 1;
        }
    }

    protected static int configureMultiplier(String rate, int numberOfDecimals) {
        int multiplier = 1;
        if (numberOfDecimals != 1){
            for (int x = numberOfDecimals; x > 0; x--) {
                multiplier *= 10;
            }
        }else if (rate.charAt(2) != '0'){
            multiplier = 10;
        }
        return multiplier;
    }

    protected static int decimalsLeftOfDecimalPoint(String rate) {
        return (rate.length() - (rate.indexOf('.') + 1));
    }

    protected boolean equals(Population populationToCompare){

        if (population.length != populationToCompare.population.length) return false;

        for (int x = 0; x < population.length && x < populationToCompare.population.length; x++){
            if (!population[x].equals(populationToCompare.population[x])){
                return false;
            }
        }

        return true;
    }

    protected Individual getBestInPopulation(){

        Individual max = population[0];
        double fitness = 0;
        for (int x = 0; x < population.length; x++){
            fitness = population[x].getFitness();
            if (fitness > max.getFitness()){
                max = population[x];
            }
        }
        return max;
    }
}
