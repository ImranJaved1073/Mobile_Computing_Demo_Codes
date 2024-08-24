CREATE TABLE [dbo].[Students] (
    [Name]   NVARCHAR (100) NOT NULL,
    [RollNo] NVARCHAR (50)  NOT NULL,
    [CGPA]   FLOAT (53)     NOT NULL,
    [Age]    INT            NOT NULL,
    PRIMARY KEY CLUSTERED ([RollNo] ASC)
);

