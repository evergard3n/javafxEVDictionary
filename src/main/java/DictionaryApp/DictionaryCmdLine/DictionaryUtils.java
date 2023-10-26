package DictionaryApp.DictionaryCmdLine;

public class DictionaryUtils  {

    public static String showAllWords() {
        StringBuilder sb = new StringBuilder();
        for (Word w: Dictionary.dictionary) {

            sb.append(w.toString()).append("\n");
        }
        return sb.toString();
    }
//    public static String findSimilarWord() {
//        Dictionary temp = new Dictionary();
//        for(Word w : Dictionary.dictionary) {
//
//        }
//    }
    public static void dictionaryBasic() {


        int op = 0;

        do {
            System.out.println("Choose your character:");
            System.out.println("1. Insert word from command line");
            System.out.println("2. Show All Word");
            System.out.println("3. Look up");
            System.out.println("4. Close");
            op = DictionaryManagement.scn.nextInt();
            DictionaryManagement.scn.nextLine();
            switch (op) {
                case 1 -> {
                    DictionaryManagement.insertFromCmdline();
                }
                case 2 -> {
                    System.out.println("Showing all words");
                    System.out.println(showAllWords());
                }
                case 4 -> {
                    DictionaryManagement.EXIT();
                }
                case 3 -> {
                    System.out.println("Enter your word");
                    String target = DictionaryManagement.scn.nextLine();
                    System.out.println(DictionaryManagement.dictionaryLookUp(target));

                }
                default -> {
                    System.out.println("Invalid Operation!");
                }
            }
            System.out.println("Do you wish to continue? YES / NO");
            String string = DictionaryManagement.scn.nextLine();
            if (string.equals("YES")) {
                dictionaryBasic();

            } else if (string.equals("NO")) {
                DictionaryManagement.EXIT();
            }
        }
        while (op != 0);




    }

    public static void dictionaryAdvanced() {


        int op = 0;

        do {
            System.out.println("Welcome to my dictionary!:");
            System.out.println("1. Add New Word");
            System.out.println("2. Show All Word");
            System.out.println("3. Look up");
            System.out.println("4. Remove Word");
            System.out.println("5. Update Word");
            System.out.println("6. Import From File");
            System.out.println("7. Export To File");
            System.out.println("8. Exit");
            op = DictionaryManagement.scn.nextInt();
            DictionaryManagement.scn.nextLine();
            switch (op) {
                case 1 -> {
                    DictionaryManagement.insertFromCmdline();
                }
                case 2 -> {
                    System.out.println("Showing all words");
                    System.out.println(showAllWords());
                }
                case 4 -> {
                    System.out.println("Enter the word you want to delete: ");
                    String target = DictionaryManagement.scn.nextLine();
                    DictionaryManagement.deleteWord(target);
                }
                case 3 -> {
                    System.out.println("Enter your word");
                    String target = DictionaryManagement.scn.nextLine();
                    System.out.println(DictionaryManagement.dictionaryLookUp(target));

                }
                case 5-> {
                    System.out.println("Enter the word you want to update: ");
                    String target = DictionaryManagement.scn.nextLine();
                    DictionaryManagement.updateWord(target);
                }
                case 6 -> {
                    System.out.println("Imported from src/dictionaries.txt");
                    DictionaryManagement.insertFromFile("src/dictionaries.txt");
                }
                case 7 -> {
                    System.out.println("Exported to src/dictionaries.out");
                    DictionaryManagement.exportToFile("src/dictionaries.out");
                }
                case 8 -> {
                    DictionaryManagement.EXIT();
                }
                case 9 -> {

                }
                default -> {
                    System.out.println("Invalid Operation!");
                }
            }
            System.out.println("Do you wish to continue? YES / NO");
            String string = DictionaryManagement.scn.nextLine();
            if (string.equals("YES")) {
                dictionaryAdvanced();

            } else if (string.equals("NO")) {
                DictionaryManagement.EXIT();
            }
        }
        while (op != 0);




    }
}

