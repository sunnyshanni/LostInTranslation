package translation;

import javax.swing.*;
import java.awt.event.*;

// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            String[] languages;
            languages = languageConverter.allLanguageNames();
            JComboBox<String> languageComboBox = new JComboBox<>(languages);
            languagePanel.add(languageComboBox);


            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));

            String[] countries;
            countries = countryConverter.allCountryNames();
            JList<String> CountriesList = new JList<>(countries);
            JScrollPane CountriesScrollPane = new JScrollPane(CountriesList);
            CountriesScrollPane.addMouseListener(new MouseAdapter() {});
            CountriesScrollPane.setVisible(true);
            CountriesScrollPane.setSize(CountriesList.getWidth(), CountriesList.getHeight());
            countryPanel.add(CountriesScrollPane);


            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String language =  (String) languageComboBox.getSelectedItem();
                    String languageCode = languageConverter.fromLanguage(language).toLowerCase();
                    String country = CountriesList.getSelectedValue();
                    String countryCode = countryConverter.fromCountry(country).toLowerCase();


                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();


                    String result = translator.translate(countryCode, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(countryPanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
