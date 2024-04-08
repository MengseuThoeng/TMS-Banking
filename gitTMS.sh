# Add all files to the staging area
git add .

# Get the current date and time
current_datetime=$(date +"%Y-%m-%d %T")

# Commit changes with the current date and time in the commit message
git commit -m "Update changes - $current_datetime"

# Push changes to the remote repository
git push origin master
