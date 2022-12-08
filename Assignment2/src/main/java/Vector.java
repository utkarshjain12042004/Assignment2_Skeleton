import java.util.Arrays;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 1.1
        this.doubElements = _elements;
    }
    public double getElementatIndex(int _index) {
        //TODO Task 1.2
        if (_index >= doubElements.length) { return -1; }
        else { return doubElements[_index]; }
    }
    public void setElementatIndex(double _value, int _index) {
        //TODO Task 1.3
        if (_index >= doubElements.length) { this.doubElements[doubElements.length-1] = _value; }
        else { doubElements[_index] = _value; }
    }
    public double[] getAllElements() {
        //TODO Task 1.4
        return this.doubElements;
    }
    public int getVectorSize() {
        //TODO Task 1.5
        return doubElements.length;
    }
    public Vector reSize(int _size) {
        //TODO Task 1.6
        if (_size <=0 || _size == doubElements.length){ return new Vector(doubElements); }
        else{
            double[] elements = new double[_size];
            for (int i = 0; i <= _size-1; i++){
                if (i >= doubElements.length){ elements[i] = -1; }
                else { elements[i] = doubElements[i]; }
            }
            return new Vector(elements);
        }
    }
    public Vector add(Vector _v) {
        //TODO Task 1.7
        Vector _v0 = new Vector(doubElements);
        if (_v.getVectorSize() >= _v0.getVectorSize()){
            _v0.reSize(_v.getVectorSize());
            for (int i = 0; i <= _v.getVectorSize()-1; i++){
                _v.setElementatIndex(_v.getElementatIndex(i)+_v0.getElementatIndex(i), i);
            }
            return _v;
        }
        else {
            _v.reSize(_v0.getVectorSize());
            for (int i = 0; i <= _v0.getVectorSize()-1; i++){
                _v0.setElementatIndex(_v.getElementatIndex(i)+_v0.getElementatIndex(i), i);
            }
            return _v0;
        }
    }
    public Vector subtraction(Vector _v) {
        //TODO Task 1.8
        Vector _v0 = new Vector(doubElements);
        if (_v.getVectorSize() >= _v0.getVectorSize()){
            _v0.reSize(_v.getVectorSize());
            for (int i = 0; i <= _v.getVectorSize()-1; i++){
                _v.setElementatIndex(_v0.getElementatIndex(i)-_v.getElementatIndex(i), i);
            }
            return _v;
        }
        else {
            _v.reSize(_v0.getVectorSize());
            for (int i = 0; i <= _v0.getVectorSize()-1; i++){
                _v0.setElementatIndex(_v0.getElementatIndex(i)-_v.getElementatIndex(i), i);
            }
            return _v0;
        }
    }

    public double dotProduct(Vector _v) {
        //TODO Task 1.9
        Vector _v0 = new Vector(doubElements);
        double results = 0;
        if (_v.getVectorSize() >= _v0.getVectorSize()){
            _v0.reSize(_v.getVectorSize());
            for (int i = 0; i <= _v.getVectorSize()-1; i++){
                results += _v0.getElementatIndex(i)*_v.getElementatIndex(i);
            }
        }
        else {
            _v.reSize(_v0.getVectorSize());
            for (int i = 0; i <= _v0.getVectorSize()-1; i++){
                results += _v0.getElementatIndex(i)*_v.getElementatIndex(i);
            }
        }
        return results;
    }
    public double cosineSimilarity(Vector _v) {
        //TODO Task 1.10
        Vector _v0 = new Vector(doubElements);
        double _v0Squared = 0;
        double _vSquared = 0;
        if (_v.getVectorSize() >= _v0.getVectorSize()){
            _v0.reSize(_v.getVectorSize());
            for (int i = 0; i <= _v.getVectorSize()-1; i++){
                _vSquared += Math.pow(_v0.getElementatIndex(i), 2);
                _v0Squared += Math.pow(_v.getElementatIndex(i), 2);
            }
        }
        else {
            _v.reSize(_v0.getVectorSize());
            for (int i = 0; i <= _v0.getVectorSize()-1; i++){
                _v0Squared += Math.pow(_v.getElementatIndex(i), 2);
                _vSquared += Math.pow(_v0.getElementatIndex(i), 2);
            }
        }
        return dotProduct(_v) / (Math.sqrt(_vSquared) * Math.sqrt(_v0Squared));
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;
        //TODO Task 1.11
        Vector _v0 = new Vector(doubElements);
        int i = 0;
        if (v.getVectorSize() != _v0.getVectorSize()){ return false; }
        else {
            while (boolEquals && i <= v.getVectorSize()){
                boolEquals = (v.getElementatIndex(i) == _v0.getElementatIndex(i));
                i++;
            }
        }
        return boolEquals;
    }
    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}

