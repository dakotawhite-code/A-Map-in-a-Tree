class SplayNode {
    String key;
    String value;
    SplayNode left, right;
    
    public SplayNode(String key, String value) {
        this.key = key;
        this.value = value;
        this.left = this.right = null;
    }
}

class Splay {
    private SplayNode root;
    public Splay() {
        root = null;
    }
    private SplayNode right(SplayNode n1) {
        SplayNode n2 = n1.left;
        n1.left = n2.right;
        n2.right = n1;
        return n2;
    }
    private SplayNode left(SplayNode n1) {
        SplayNode n2 = n1.right;
        n1.right = n2.left;
        n2.left = n1;
        return n2;
    }
    private SplayNode splay(SplayNode root, String key) {
        if (root == null || root.key.equals(key)) {
            return root;
        }
        if (root.key.compareTo(key) > 0) {
            if (root.left == null){ 
            return root;
        }
            if (root.left.key.compareTo(key) > 0) {
                root.left.left = splay(root.left.left, key);
                root = right(root);
            } else if (root.left.key.compareTo(key) < 0) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null) root.left = left(root.left);
            }
            return (root.left == null) ? root : right(root);
        } else {
            if (root.right == null){
                return root;
            }
                if (root.right.key.compareTo(key) > 0) {
                root.right.left = splay(root.right.left, key);
                    if (root.right.left != null) root.right = right(root.right);
            } else if (root.right.key.compareTo(key) < 0) {
                root.right.right = splay(root.right.right, key);
                root = left(root);
            }
            return (root.right == null) ? root : left(root);
        }
    }
    public void insert(String key, String value) {
        if (root == null) {
            root = new SplayNode(key, value);
            return;
        }
        root = splay(root, key);
        if (root.key.equals(key)) {
            root.value = value;
            return;
        }
        SplayNode n3 = new SplayNode(key, value);
        if (root.key.compareTo(key) > 0) {
            n3.right = root;
            n3.left = root.left;
            root.left = null;
        } else {
            n3.left = root;
            n3.right = root.right;
            root.right = null;
        }
        root = n3;
    }

    public String get(String key) {
        if (root == null){
        return "";
        }
            root = splay(root, key);
        if (root.key.equals(key)) {
            return root.value;
        }
        return "null";
    }
    public boolean delete(String key) {
        if (root == null){
            return false;
        }
            root = splay(root, key);
            if (root.key.equals(key)) {
                if (root.left == null) {
                    root = root.right;
            } else {
                SplayNode temp = root.right;
                root = root.left;
                splay(root, key);
                root.right = temp;
            }
            return true;
        }
        return false;
    }
}
class TreeMap {
    private Splay splayTree;
    public TreeMap() {
        splayTree = new Splay();
    }
    public void insert(String key, String value) {
        splayTree.insert(key, value);
    }
    public String get(String key) {
        return splayTree.get(key);
    }
    public boolean delete(String key) {
        return splayTree.delete(key);
    }
    public static void main(String[] args) {
        TreeMap map = new TreeMap();
        
        map.insert("keyOne", "valueOne");
        map.insert("keyTwo", "valueTwo");
        map.insert("keyThree", "valueThree");
     
        System.out.println(map.get("keyOne"));
        System.out.println(map.get("keyThree"));
        System.out.println(map.get("keyDoesNotExist"));
        System.out.println(map.delete("keyOne"));
      }
    }