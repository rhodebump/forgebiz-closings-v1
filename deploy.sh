#!/bin/bash

#ant -f ./wordpress/plugins/forgebiz-closings/ClosingsApp/build.xml build

#zip up the plugin
cd ./wordpress/plugins/
zip -r forgebiz-closings.zip forgebiz-closings/
cd ../..

#chmod g+w  /home/prhodes/local/libs/apache/forgebizdemo/www/wp-content/uploads
rsync -avz ./wordpress/plugins/forgebiz-closings.zip  prhodes@demo.forgebiz.com:/home/prhodes/local/libs/apache/forgebiz/www/wp-content/uploads/

#rsync it to the demo box

rsync -avz ./wordpress/plugins/forgebiz-closings  prhodes@demo.forgebiz.com:/home/prhodes/local/libs/apache/forgebizdemo/www/wp-content/plugins/