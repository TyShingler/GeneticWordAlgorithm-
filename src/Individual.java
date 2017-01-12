import java.lang.Math;


public class Individual {

//    private int geneSize = 0;
    private String genes = "";
    private double fitness = 0;
    
    public Individual(int geneSize) {
//        this.geneSize = geneSize;
        for (int x = 0; x < geneSize; x++) {
            genes += randomChar();
        }
    }

    protected Individual(String genes) {
        this.genes = genes;
    }

    public double calculateFitness(String target){
        int fitness = 0;
        int leftOver = 0;
        int x;
        for (x = 0; x < genes.length() && x < target.length(); x++) {
            if(genes.charAt(x) == target.charAt(x)){
                fitness++;
            }
        }
        for (x = x; x < genes.length() || x < target.length() ;x++, leftOver++);

        double min = Math.min(genes.length(), target.length());

        this.fitness = fitness/((double)min + leftOver);

        return this.fitness;
    }

    public double getFitness(){
        return fitness;
    }

    protected static char randomChar(){
        int range = (126 - 32);
        int charAsInt = (int) (Math.random() * range) + 32;
        if (charAsInt == 34 ) { charAsInt = 32; }
        return (char) charAsInt;
    }

    public String getGenes(){
        return genes;
    }

    public static Individual mate(Individual parentOne, Individual parentTwo, int[] mutationArray){
        String gene = singleCrossoverMate(parentOne, parentTwo);
        gene = mutate(gene, mutationArray);
        return new Individual(gene);
    }

    protected static String singleCrossoverMate(Individual parentOne, Individual parentTwo) {
        int midpoint = (int) (Math.random()*parentOne.getGenes().length());
//        System.out.print(midpoint+  ", ");
        String newGene = parentOne.getGenes().substring(0, midpoint)
                + parentTwo.getGenes().substring(midpoint);
        return newGene;
    }

    protected static String mutate(String newGene, int[] mutationArray){
        String gene = "";
        for (int x = 0; x < newGene.length(); x++){
            if (mutationArray[(int)(Math.random()*mutationArray.length)] == 1){
                gene += randomChar();
            }else {
                gene += newGene.charAt(x);
            }
        }
        return gene;
    }

    protected boolean equals(Individual individualToCompare){

        if (getGenes().length() != individualToCompare.getGenes().length()) return false;

        for (int x = 0; x < getGenes().length() && x < individualToCompare.getGenes().length(); x++){
            if (getGenes().charAt(x) != individualToCompare.getGenes().charAt(x)){
                return false;
            }
        }

        return true;
    }
}
