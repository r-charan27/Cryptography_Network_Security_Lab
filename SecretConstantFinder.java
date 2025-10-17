import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/**
 * Fraction class for performing arithmetic on rational numbers
 */
class Fraction {
    BigInteger num; // numerator
    BigInteger den; // denominator (always positive)

    public Fraction(BigInteger num, BigInteger den) {
        if (den.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        // Ensure denominator is positive
        if (den.signum() < 0) {
            num = num.negate();
            den = den.negate();
        }
        BigInteger gcd = num.gcd(den);
        if (!gcd.equals(BigInteger.ZERO)) {
            this.num = num.divide(gcd);
            this.den = den.divide(gcd);
        } else {
            this.num = num;
            this.den = den;
        }
    }
    
    // Constructor for an integer value
    public Fraction(BigInteger num) {
        this(num, BigInteger.ONE);
    }
    
    public Fraction add(Fraction other) {
        BigInteger numerator = this.num.multiply(other.den).add(other.num.multiply(this.den));
        BigInteger denominator = this.den.multiply(other.den);
        return new Fraction(numerator, denominator);
    }
    
    public Fraction multiply(Fraction other) {
        return new Fraction(this.num.multiply(other.num), this.den.multiply(other.den));
    }
    
    public Fraction divide(Fraction other) {
        if (other.num.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Division by zero");
        }
        return new Fraction(this.num.multiply(other.den), this.den.multiply(other.num));
    }
    
    public Fraction negate() {
        return new Fraction(this.num.negate(), this.den);
    }
    
    public String toString() {
        if (den.equals(BigInteger.ONE))
            return num.toString();
        return num + "/" + den;
    }
}

/**
 * A simple class to store a point (x, y) as BigIntegers.
 */
class Point {
    BigInteger x;
    BigInteger y;
    
    public Point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
}

public class SecretConstantFinder {

    // Method to decode a number in a given base from string using BigInteger
    public static BigInteger decodeValue(String value, int base) {
        return new BigInteger(value, base);
    }

    // Read file content into a string
    public static String readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
    
    // Parse JSON input and extract points.
    // We assume the JSON structure has a "keys" object that contains "n", "k", and the roots.
    public static List<Point> getPoints(JSONObject keysObj, int kRequired) {
        List<Point> points = new ArrayList<>();
        // iterate over keys in the "keys" object
        Iterator<String> it = keysObj.keys();
        while(it.hasNext()){
            String key = it.next();
            // skip keys "n" and "k" (or any key that does not hold a JSON object)
            if(key.equals("n") || key.equals("k"))
                continue;
            // Sometimes the key might not be convertible to integer.
            // For our purposes, we assume the key (x-coordinate) is numeric.
            BigInteger x;
            try {
                x = new BigInteger(key);
            } catch(NumberFormatException e) {
                // If not numeric, skip it.
                continue;
            }
            JSONObject pointObj = keysObj.getJSONObject(key);
            String baseStr = pointObj.getString("base");
            String valueStr = pointObj.getString("value");
            int base = Integer.parseInt(baseStr);
            BigInteger y = decodeValue(valueStr, base);
            points.add(new Point(x, y));
        }
        // If more than k points are provided, we use the first k points.
        while (points.size() > kRequired) {
            points.remove(points.size() - 1);
        }
        return points;
    }
    
    // Compute f(0) using Lagrange interpolation formula.
    // f(0) = sum_{i} y_i * l_i(0) where l_i(0) = product_{j != i} (0 - x_j)/(x_i - x_j)
    public static BigInteger interpolateConstant(List<Point> points) {
        Fraction result = new Fraction(BigInteger.ZERO);
        int n = points.size();
        
        for (int i = 0; i < n; i++) {
            Fraction li = new Fraction(BigInteger.ONE);
            for (int j = 0; j < n; j++) {
                if(i == j) continue;
                // term = (-x_j) / (x_i - x_j)
                Fraction term = new Fraction(points.get(j).x.negate(), points.get(i).x.subtract(points.get(j).x));
                li = li.multiply(term);
            }
            // Multiply by y_i
            Fraction termVal = li.multiply(new Fraction(points.get(i).y));
            result = result.add(termVal);
        }
        
        // Since we expect an integer constant, the denominator should be 1.
        if(!result.den.equals(BigInteger.ONE)) {
            System.out.println("Warning: Result is not an integer. Fraction = " + result);
        }
        return result.num;
    }
    
    public static void main(String[] args) {
        // Check if filename is provided
        if(args.length < 1) {
            System.out.println("Usage: java SecretConstantFinder <input_json_file>");
            return;
        }
        String filename = args[0];
        try {
            String jsonContent = readFile(filename);
            JSONObject inputObj = new JSONObject(jsonContent);
            // Assuming the JSON contains a "keys" object
            JSONObject keysObj = inputObj.getJSONObject("keys");
            
            // Get k value (minimum number of roots required)
            int kRequired = keysObj.getInt("k");
            // Get points list
            List<Point> points = getPoints(keysObj, kRequired);
            
            if(points.size() < kRequired) {
                System.out.println("Not enough roots provided. Required: " + kRequired + ", provided: " + points.size());
                return;
            }
            
            // Compute the secret (constant term)
            BigInteger secret = interpolateConstant(points);
            System.out.println("The secret constant (c) is: " + secret);
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing JSON or computing secret: " + e.getMessage());
            e.printStackTrace();
        }
    }
}