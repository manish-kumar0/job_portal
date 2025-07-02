import React, { useEffect, useState } from "react";
import { motion } from "framer-motion";
import { Link } from "react-router-dom";
import JobCard from "./JobCard";
import { Button } from "@/components/ui/button";
import { ArrowRight } from "lucide-react";
import publicApi from "@/api/publicApi";
import jobseekerApi from "@/api/JobseekerApi";
import { useAuth } from "@/context/AuthContex";

export default function LatestJobs() {
  const { jobs, setJobs, appliedJobs, setAppliedJobs, user } = useAuth(); // 🧠 Add user from context
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  // ✅ Secure applied jobs fetch only for Jobseeker
  useEffect(() => {
    const fetchAppliedJobs = async () => {
      if (!user || user.role !== "jobseeker") return; // ❌ prevent unauthorized 403

      try {
        const res = await jobseekerApi.get("/get-all-applied-jobs");
        setAppliedJobs(res.data);
      } catch (err) {
        console.error("Error fetching applied jobs:", err);
        setAppliedJobs([]); // fallback to empty
      }
    };

    fetchAppliedJobs();
  }, [user, setAppliedJobs]);

  // ✅ Fetch latest jobs
  useEffect(() => {
    const fetchJobs = async () => {
      try {
        const res = await publicApi.get("/find-all-jobs");

        if (res.data && Array.isArray(res.data)) {
          const latestJobs = res.data
            .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
            .slice(0, 6);
          setJobs(latestJobs);
        } else {
          setError("No jobs available");
        }
      } catch (error) {
        setError("Failed to fetch jobs");
      } finally {
        setIsLoading(false);
      }
    };

    fetchJobs();
  }, [setJobs]);

  return (
    <section className="max-w-7xl mx-auto max-[1300px]:px-2 py-8 lg:py-24">
      <div className="text-center space-y-4">
        <motion.h2
          className="text-3xl md:text-4xl font-medium"
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.5 }}
        >
          Latest Jobs Post
        </motion.h2>
        <motion.p
          className="text-lg font-medium text-muted-foreground max-w-2xl mx-auto"
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.5, delay: 0.2 }}
        >
          Explore the different types of available jobs to apply
          <br className="hidden lg:block" />
          discover which is right for you.
        </motion.p>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 mt-12">
        {isLoading ? (
          <p className="text-center col-span-full">Loading jobs...</p>
        ) : error ? (
          <p className="text-center col-span-full text-red-500">{error}</p>
        ) : jobs.length === 0 ? (
          <p className="text-center col-span-full">No jobs available</p>
        ) : (
          jobs.map((job) => <JobCard key={job.id} job={job} />)
        )}
      </div>

      <div className="flex justify-center mt-12">
        <Button variant="outline" size="lg" className="group" asChild>
          <Link to="/jobs" className="flex items-center gap-2">
            See All Jobs
            <ArrowRight className="h-4 w-4 transition-transform group-hover:translate-x-1" />
          </Link>
        </Button>
      </div>
    </section>
  );
}
