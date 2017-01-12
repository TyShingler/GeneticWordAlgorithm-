public class Run {

    static String target = "star citizen";

    static int populationSize = 100;

    static int mutationRate = 1;

    static Population population;

    static String bestResult = "";

    public static void main(String[] args){

        int generation = 0;

        createPopulation();
        calculateFitness();
        boolean finished = isFinished();
        while(!finished){
            reproduction();
            calculateFitness();
            finished = isFinished();

            Individual best = population.getBestInPopulation();
            System.out.println("Generation: " + generation + " Best: " + best.getGenes() + " Score: " + best.getFitness());
            generation++;
        }
        displayResults();
    }



    private static void createPopulation() {
        population = new Population(target, populationSize, mutationRate);
    }

    private static void calculateFitness() {
        population.calculateFitness();
    }

    private static void reproduction(){
        population.naturalSelection();
    }

    private static boolean isFinished(){
        Individual individual = population.isFinished();
        if(individual != null){
            bestResult = individual.getGenes();
            return true;
        }else{
            return false;
        }
    }

    private static void displayResults(){
        System.out.println(">>>Done<<< Final result: " + bestResult);
    }

}
