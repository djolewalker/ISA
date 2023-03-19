module.exports = {
  branches: ["main", { name: "develop", prerelease: true }],
  repositoryUrl: "https://github.com/djolewalker/ISA",
  plugins: [
    "@semantic-release/commit-analyzer",
    "@semantic-release/release-notes-generator",
    "@semantic-release/github",
    "semantic-release-export-data",
  ],
};
