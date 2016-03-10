NAME = "Main"

all:
	@echo "Compiling..."
	javac main/*.java main/model/*/*.java main/view/*/*.java

run: all
	@echo "Running..."
	java main/$(NAME)

clean:
	rm -rf main/*.class
	rm -rf main/model/*/*.class
	rm -rf main/view/*/*.class