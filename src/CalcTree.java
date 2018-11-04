public class CalcTree {

    private String origin;
    private CalcTree left;
    private CalcTree right;

    public CalcTree(String origin, CalcTree left, CalcTree right){
        this.origin = origin;
        this.left = left;
        this.right = right;
    }

    public CalcTree getLeft(){
        return this.left;
    }

    public CalcTree getRight(){
        return this.right;
    }

    public String getOrigin(){
        return this.origin;
    }


}
