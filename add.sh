cd /home/emilio/code/Reviewer
java -cp /home/emilio/code/Reviewer/out/production/Reviewer/ core.EventAdd
git status
git add .
dayTime=`date +%Y-%m-%d`
git commit -m "add new events for $dayTime"
git push
