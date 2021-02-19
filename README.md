# Medical Data Parser

## About
The following project is designed to read in a CSV data file and format the patient's data entries into chronological order and then assess which records have more than X number of consecutive years, the output of which is recorded in two separate files for manual assessment.

Some data clusters have a varying amount of fields, so the program dynamically assesses which fields are missing and adds them to the end file. A summary of grading frequencies is also created, in descening chronological order from the n-th grading encouter.

## Instructions
1. Download the repository to your local machine.
2. Add your data csv file to the `/data` folder and rename it `data.csv`.
3. Set your working directory to the MedicalDataParser's directory.
4. Compile the program, using `javac *.java`.
5. Run the program, using `java Main`.
6. Output will be generated into the respective folders in `/data`.
