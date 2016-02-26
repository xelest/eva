package com.amihaiemil.eva.ex;

import com.amihaiemil.eva.Solution;
import com.amihaiemil.eva.SolutionsGenerator;

import java.util.Random;

/**
 * @author Mihai Andronache (amihaiemil@gmail.com)
 */
public class BinaryArraySolutionsGenerator implements SolutionsGenerator {
    private int size;
    private Random r = new Random();
    public BinaryArraySolutionsGenerator(int size) {
        this.size = size;
    }
    public Solution generateRandomSolution() {
        NumericalRepresentation representation = new NumericalRepresentation();
        for(int i=0;i<size;i++) {
            representation.addNumber(r.nextInt(2));
        }
        Solution generated = new IntegersArraySolution();
        generated.setRepresentation(representation);
        return generated;
    }
}
