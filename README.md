# File-Retrieval-Engine

The File Retrieval Engine is a Java-based application designed to index and search files within specified datasets. It follows a layered architecture consisting of three main components: AppInterface, ProcessingEngine, and IndexStore. This README provides an overview of the File Retrieval Engine, its features, usage instructions, and other relevant information.

## Features

- Indexing of files from specified dataset paths.
- Processing search queries expressed as AND queries to retrieve relevant files.
- Sorting search results based on the occurrence frequency of terms in files.
- Command-line interface for user interaction.
- Support for quitting the application gracefully.

## Components

### 1. AppInterface
Responsible for implementing the command-line interface to interact with the File Retrieval Engine. It interprets indexing and search commands submitted by the user, forwards them to the ProcessingEngine, and displays the results on the screen.

### 2. ProcessingEngine
Handles indexing operations by extracting alphanumeric terms from dataset files and building an index that stores term-document mappings with frequency information. It also processes search queries, retrieves relevant files based on the terms, and sorts the results by occurrence frequency.

### 3. IndexStore
Stores and manages the index data structure, which consists of mappings between terms and documents along with their frequencies. It provides support for updating the index and performing single-term lookup operations.

## Usage

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Build the project using Maven: `mvn clean install`.
4. Run the application: `mvn exec:java`.

### Commands
- `index <dataset path>`: Indexes files from the specified dataset path.
- `search <AND query>`: Searches for files containing all terms in the AND query.
- `quit`: Closes the application.

## Datasets
The File Retrieval Engine supports indexing and searching files from the Gutenberg Project Datasets, which contain ASCII TXT documents representing books. Five datasets with varying sizes and file counts are included for evaluation purposes.

## Performance Evaluation
To evaluate the indexing performance, run the program for each dataset and measure the wall time taken for indexing. Calculate the indexing throughput in MB/s by dividing the total dataset size by the total indexing execution time. The differences between wall time and CPU time, dataset sizes, data structures used in IndexStore, and the IO-intensive nature of the ProcessingEngine are discussed in detail in the evaluation section.
