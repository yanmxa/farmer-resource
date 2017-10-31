package com.aaron.jmetal.nsga2;

import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.SimpleRandomMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

public class SolutionsEvoling{
    Problem<DoubleSolution> problem;  
    CrossoverOperator<DoubleSolution> crossover;  
    MutationOperator<DoubleSolution> mutation;  
    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;  
    Algorithm<List<DoubleSolution>> algorithm; 
    
    List<DoubleSolution> population;
    
    public SolutionsEvoling(){
    	this(25000,100);
    }
    
    public SolutionsEvoling(int iteratorTimes, int populationSize){
    	// 定义优化问题
    	problem = new FarmerProblem();
    	
    	// 配置交叉算子:交叉算子用SBXcrossover
        double crossoverProbability = 0.9;  
        double crossoverDistributionIndex = 20.0;  
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex); 
    	
        //配置变异算子:变异算子用SimpleRandomMutation（随机变异）  
        double mutationProbability = 1.0 / problem.getNumberOfVariables();  
        //  double mutationDistributionIndex = 20.0 ;  
        mutation = new SimpleRandomMutation(mutationProbability);  
        
        //配置选择算子:选择算子采用二元锦标赛选择法 
        selection = new BinaryTournamentSelection<DoubleSolution>(  
                new RankingAndCrowdingDistanceComparator<DoubleSolution>());
        
        //将组件注册到algorithm  
        algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation)  
                .setSelectionOperator(selection)  
                .setMaxEvaluations(iteratorTimes)  
                .setPopulationSize(populationSize)  
                .build();  
/*       或者用这样的方法注册一个算法 
          evaluator = new SequentialSolutionListEvaluator<DoubleSolution>(); 
          algorithm = new NSGAII<DoubleSolution>(problem, 25000, 100, crossover, 
          mutation, selection, evaluator); 
*/  
        //用AlgorithmRunner运行算法  
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();  
        //获取结果集  
        population = algorithm.getResult();  
//        long computingTime = algorithmRunner.getComputingTime();  
    }
    
    public List<DoubleSolution> getPopulation(){
    	return algorithm.getResult();
    }
    
//    public Solution getBestSolutionByWeights(double[] weights){
//    	Solution result = population.get(0);
//    	double objetiveVlaue = 0;
//    	for (int i=0; i<result.getNumberOfObjectives(); i++) 
//    		objetiveVlaue += weights[i] * population.get(0).getObjective(i);
//    	
//    	for ( int i=1; i<population.size(); i++) {
//    		double temp = 0;
//        	for (int j=0; j<result.getNumberOfObjectives(); j++) 
//        		temp += weights[j] * population.get(i).getObjective(j);
//        	if (temp < objetiveVlaue) result = population.get(i);
//    	}
//    	
//    	return result;
//    }
}
