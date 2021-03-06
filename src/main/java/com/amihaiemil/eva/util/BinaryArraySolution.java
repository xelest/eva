/*
 Copyright (c) 2016, Mihai-Emil Andronache
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification,
 are permitted provided that the following conditions are met:
 1. Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.
 3. Neither the name of the copyright holder nor the names of its contributors
 may be used to endorse or promote products derived from this software
 without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED.
 IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.eva.util;

import java.util.Random;

import com.amihaiemil.eva.Solution;

/**
 * A Solution represented on an array of 0 and 1 (NumericalRepresentation is used).
 * @author Mihai Andronache (amihaiemil@gmail.com).
 */
public class BinaryArraySolution extends Solution {
    private Random chance;

    public BinaryArraySolution() {
    	this.chance = new Random();
    }
    
    public BinaryArraySolution(NumericalRepresentation representation) {
    	this.chance = new Random();
    	for(int i=0;i<representation.getSize();i++) {
    		if(representation.get(i) != 0 && representation.get(i) != 1) {
    			throw new IllegalArgumentException(
    				"Expected a numerical representation with 0s and 1s only!"
    			);
    		}
    	}
    	this.setRepresentation(representation);
    }
    
    public BinaryArraySolution(Random chance) {
        this.chance = chance;
    }

    @Override
    public void mutate(double pm) {
        NumericalRepresentation representation = (NumericalRepresentation) this.getRepresentation();
        for(int i=0;i<representation.getSize();i++) {
            if(chance.nextDouble() < pm) {
                representation = representation.replaceAt(i, 1-representation.get(i));
            }
        }
        this.setRepresentation(representation);
    }

    @Override
    public Solution crossover(Solution partner, double cp) {
    	if(chance.nextDouble() < cp) { //parents have an offspring
    		NumericalRepresentation representation = (NumericalRepresentation) this.getRepresentation();
            NumericalRepresentation partnerRepresentation = (NumericalRepresentation) partner.getRepresentation();

            Solution offspring = new BinaryArraySolution(new Random());
            NumericalRepresentation offSpringRepresentation = new NumericalRepresentation();
            int point = chance.nextInt(representation.getSize());
            for(int i=0;i<point;i++) {
                offSpringRepresentation = offSpringRepresentation.addNumber(representation.get(i));
            }
            for(int i=point;i<representation.getSize();i++) {
                offSpringRepresentation = offSpringRepresentation.addNumber(partnerRepresentation.get(i));
            }
            offspring.setRepresentation(offSpringRepresentation);
            return offspring;
        } else { //no offspring, return best parent.
            if(this.getFitness().compareTo(partner.getFitness()) == 1) {
                return this;
            }
            return partner;
        }

    }

}
