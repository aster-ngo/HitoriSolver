package duongngo.hitori.com;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class SatSolver {

	private static String resultCode;
	private static ArrayList<Integer> result;
	private static boolean status;
	private static String Url = "src\\filesat\\Sat.cnf";
	
	public SatSolver(){
		result = new ArrayList<Integer>();
	}
	

	public ArrayList<Integer> getResult() {
		return result;
	}

	public boolean isStatus() {
		return status;
	}

	@SuppressWarnings("deprecation")
	public void readCNF() {
		ISolver solver = SolverFactory.newLight();
		solver.setTimeout(3600); // 1 hour timeout
		solver.setDBSimplificationAllowed(true);
		Reader reader = new DimacsReader(solver);
		try {
			IProblem problem = reader.parseInstance(Url);
			if (problem.isSatisfiable()) {
				status = true;
				resultCode = reader.decode(problem.model());
			} else {
				resultCode = "";
				status = false;
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException !");
		} catch (ParseFormatException e) {
			System.out.println("ParseFormatException !");
		} catch (IOException e) {
			System.out.println("IOException !");
		} catch (ContradictionException e) {
			System.out.println("Unsatisfiable (trivial)!");
		} catch (TimeoutException e) {
			System.out.println("Timeout, sorry!");
		}
		
	}
	
	public void analysisString(){
		if(status == true && resultCode != null){
			resultCode = resultCode.replace(" 0", "");
			String[] number = resultCode.split(" ");
			for(int i=0; i<number.length; i++)
				result.add(Integer.parseInt(number[i]));
		}
	}
}
