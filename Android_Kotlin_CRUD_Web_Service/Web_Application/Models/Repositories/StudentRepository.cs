using Microsoft.Data.SqlClient;
namespace WebApplication4.Models.Repositories
{
    public class StudentRepository : IStudentRepository
    {
        private readonly string _connectionString;

        public StudentRepository(string connectionString)
        {
            _connectionString = connectionString;
        }

        public async Task<IEnumerable<Student>> GetAllStudentsAsync()
        {
            var students = new List<Student>();

            using (SqlConnection conn = new SqlConnection(_connectionString))
            {
                string query = "SELECT * FROM Students";
                SqlCommand cmd = new SqlCommand(query, conn);

                await conn.OpenAsync();
                SqlDataReader reader = await cmd.ExecuteReaderAsync();

                while (await reader.ReadAsync())
                {
                    students.Add(new Student
                    {
                        Name = reader["Name"].ToString(),
                        RollNo = reader["RollNo"].ToString(),
                        CGPA = (double)reader["CGPA"],
                        Age = (int)reader["Age"]
                    });
                }
            }

            return students;
        }


        public async Task AddStudentAsync(Student student)
        {
            using (SqlConnection conn = new SqlConnection(_connectionString))
            {
                string query = "INSERT INTO Students (Name, RollNo, CGPA, Age) VALUES (@Name, @RollNo, @CGPA, @Age)";
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("@Name", student.Name);
                cmd.Parameters.AddWithValue("@RollNo", student.RollNo);
                cmd.Parameters.AddWithValue("@CGPA", student.CGPA);
                cmd.Parameters.AddWithValue("@Age", student.Age);

                await conn.OpenAsync();
                await cmd.ExecuteNonQueryAsync();
                await conn.CloseAsync();
            }
        }

        public async Task<Student> GetStudentByIdAsync(int id)
        {
            Student student = null!;

            using (SqlConnection conn = new SqlConnection(_connectionString))
            {
                string query = "SELECT * FROM Students WHERE Id = @Id";
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("@Id", id);

                await conn.OpenAsync();
                SqlDataReader reader = await cmd.ExecuteReaderAsync();

                if (await reader.ReadAsync())
                {
                    student = new Student
                    {
                        Name = reader["Name"].ToString(),
                        RollNo = reader["RollNo"].ToString(),
                        CGPA = (double)reader["CGPA"],
                        Age = (int)reader["Age"]
                    };
                }
            }

            return student;
        }


        public async Task UpdateStudentAsync(Student student)
        {
            using (SqlConnection conn = new SqlConnection(_connectionString))
            {
                string query = $"UPDATE Students SET Name = @Name, CGPA = @CGPA, Age = @Age WHERE RollNo = @RollNo";
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("@Name", student.Name);
                cmd.Parameters.AddWithValue("@CGPA", student.CGPA);
                cmd.Parameters.AddWithValue("@Age", student.Age);
                cmd.Parameters.AddWithValue("@RollNo", student.RollNo);

                await conn.OpenAsync();
                await cmd.ExecuteNonQueryAsync();
                await conn.CloseAsync();
            }
        }

        public async Task DeleteStudentAsync(string rollNo)
        {
            using (SqlConnection conn = new SqlConnection(_connectionString))
            {
                string query = "DELETE FROM Students WHERE RollNo = @RollNo";
                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("@RollNo", rollNo);

                await conn.OpenAsync();
                await cmd.ExecuteNonQueryAsync();
                await conn.CloseAsync();
            }
        }
    }
}
