GRD		= ./gradlew
RUNIMG  = app/build/image/app-linux/bin/app
GIT  	= git

check:
	$(GRD) :app:check 

test:
	$(GRD) :app:clean
	$(GRD) :app:build
	$(GRD) :app:run

uberjar:
	$(GRD) :app:clean
	$(GRD) :app:uberjar

jlink:
	$(GRD) :app:clean
	$(GRD) :app:build
	$(GRD) :app:copyResources
	$(GRD) :app:jlink

run:
	$(RUNIMG)

git-add:
	$(GIT) add $(msg)

git-restore:
	$(GIT) restore $(file)

git-commit:
	$(GIT) commit -m $(msg)

git-push:
	$(GIT) push --set-upstream principal principal
