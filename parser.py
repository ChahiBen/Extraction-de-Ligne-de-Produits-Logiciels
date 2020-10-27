#AUTHOR : Ismaël SAÏ
#CONTACT : ismael.sai@etu.umontpellier.fr

#IMPORTANT : to make it works you should use the calc template provided with this code.


import sys
import csv
import re


if len(sys.argv) < 4:
    print("Error! Usage: python3 parser.py path_to_csv_file type_of_formal_context name_of_the_output_file")
    sys.exit(1)

with open(sys.argv[1], 'r') as file :
    data = re.sub('0', '', re.sub('1', 'X', re.sub('$', '|\n', re.sub('^', '|', re.sub('\n', '|\n|',re.sub(',', '|', re.sub('"', '', re.sub('\n"', '"', file.read()))))))))
f = open(sys.argv[3]+".rcft", "w")
f.write("FormalContext " + sys.argv[2] + "\n")
f.write(data)